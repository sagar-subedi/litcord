package com.sagarsubedi.litcord.service.server;

import com.sagarsubedi.litcord.Exceptions.ServerCreationConflictException;
import com.sagarsubedi.litcord.Exceptions.ServerNotFoundException;
import com.sagarsubedi.litcord.dao.MembershipRepository;
import com.sagarsubedi.litcord.dao.ServerRepository;
import com.sagarsubedi.litcord.dto.ServerDTO;
import com.sagarsubedi.litcord.model.Server;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ServerService {
    @Autowired
    ServerRepository serverRepository;

    @Autowired
    MembershipRepository membershipRepository;

    @Autowired
    private ModelMapper modelMapper;

    private final S3Client s3Client = S3Client.builder()
            .credentialsProvider(ProfileCredentialsProvider.create())
            .region(Region.AP_SOUTH_1)
            .build();

    private final String bucketName = "litcord-bucket";

    public Server createServer(String name, Long userId, MultipartFile dp) throws IOException {
        Optional<Server> server = serverRepository.findServerByNameAndUserId(name, userId);
        if(server.isPresent()){
            throw new ServerCreationConflictException();
        }
        String inviteCode = UUID.randomUUID().toString();
        byte[] compressedImage = compressImage(dp.getBytes(), 200);
        String uniqueFileName = UUID.randomUUID().toString() + ".jpg";

        // Upload to S3
        s3Client.putObject(PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(uniqueFileName)
                        .contentType("image/jpeg")
                        .build(),
                software.amazon.awssdk.core.sync.RequestBody.fromBytes(compressedImage));

        // Get URL
        URL s3Url = s3Client.utilities().getUrl(builder -> builder.bucket(bucketName).key(uniqueFileName));


        // Save to database
        Server newServer = new Server();
        newServer.setName(name);
        newServer.setDpUrl(s3Url.toString());
        newServer.setInviteCode(inviteCode);
        newServer.setUserId(userId);
        return serverRepository.save(newServer);
    }

    private byte[] compressImage(byte[] imageBytes, int maxKb) throws IOException {
        BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(imageBytes));
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        // Scale image to maintain aspect ratio
        double scaleFactor = Math.sqrt((double) maxKb * 1024 / (imageBytes.length));
        int newWidth = (int) (width * scaleFactor);
        int newHeight = (int) (height * scaleFactor);

        BufferedImage scaledImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = scaledImage.createGraphics();
        g.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
        g.dispose();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(scaledImage, "jpeg", baos);

        return baos.toByteArray();
    }
    public ServerDTO getServerById(Long id) {
        return serverRepository.findById(id)
                .map(server -> modelMapper.map(server, ServerDTO.class))
                .orElse(null);
    }

    public void updateServer(Long id, ServerDTO serverDTO) {
        Server server = serverRepository.findById(id)
                .orElseThrow();
        server.setName(serverDTO.getName());
        server.setInviteCode(serverDTO.getInviteCode());
        server.setUserId(serverDTO.getUserId());
        serverRepository.save(server);
    }

    public void deleteServer(Long id) {
        if (!serverRepository.existsById(id)) {
            throw new ServerNotFoundException("Server is not found");
        }
        serverRepository.deleteById(id);
    }

    public List<ServerDTO> getAllServers() {
        return serverRepository.findAll().stream()
                .map(server -> modelMapper.map(server, ServerDTO.class))
                .collect(Collectors.toList());
    }

    public boolean isUserAuthorized(Long userId, Long serverId) {
        return membershipRepository.existsByUserIdAndServerId(userId, serverId);
    }
}
