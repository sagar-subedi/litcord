package com.sagarsubedi.litcord.service;

import com.sagarsubedi.litcord.Exceptions.ServerCreationConflictException;
import com.sagarsubedi.litcord.Exceptions.ServerNotFoundException;
import com.sagarsubedi.litcord.dao.AccountRepository;
import com.sagarsubedi.litcord.dao.ChannelRepository;
import com.sagarsubedi.litcord.dao.MembershipRepository;
import com.sagarsubedi.litcord.dao.ServerRepository;
import com.sagarsubedi.litcord.dto.ChannelDTO;
import com.sagarsubedi.litcord.dto.ServerDTO;
import com.sagarsubedi.litcord.enums.ChannelType;
import com.sagarsubedi.litcord.enums.MembershipType;
import com.sagarsubedi.litcord.model.Account;
import com.sagarsubedi.litcord.model.Channel;
import com.sagarsubedi.litcord.model.Membership;
import com.sagarsubedi.litcord.model.Server;
import com.sagarsubedi.litcord.utils.StringUtils;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ServerService {
    @Autowired
    ServerRepository serverRepository;

    @Autowired
    ChannelRepository channelRepository;

    @Autowired
    MembershipRepository membershipRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Autowired
    private S3Presigner s3Presigner;

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
        Server createdServer = serverRepository.save(newServer);
        Channel defaultChannel = channelRepository.save(new Channel("General",createdServer.getUserId(),createdServer,ChannelType.TEXT));

        //Return Membership object not used for now
        membershipRepository.save(new Membership(createdServer.getId(), createdServer.getUserId(), MembershipType.ADMIN));
        createdServer.getChannels().add(defaultChannel);
        createdServer.setDpUrl(getServerImageUrl(createdServer.getUserId(), createdServer.getId(), StringUtils.extractFileName(createdServer.getDpUrl())));
        return createdServer;
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

    public String getServerImageUrl(Long userId, Long serverId, String dpKey) {
        if (!this.isUserAuthorized(userId, serverId)) {
//           todo: complete this so that only the member of server can access this image
//            throw new SecurityException("User is not authorized to access this server image.");
        }

        // Create a pre-signed URL valid for 24 hour
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket("litcord-bucket")
                .key(dpKey)
                .build();

        PresignedGetObjectRequest presignedRequest = s3Presigner.presignGetObject(
                builder -> builder.signatureDuration(Duration.ofHours(24))
                        .getObjectRequest(getObjectRequest)
        );

        return presignedRequest.url().toString();
    }

    public List<ServerDTO> getServersForUser(Long userId) {
        List<Server> servers = serverRepository.findAllByUserId(userId);

        // Convert to DTO
        return servers.stream()
                .map(server -> new ServerDTO(
                        server.getId(),
                        server.getName(),
                        server.getInviteCode(),
                        server.getUserId(),
                        getServerImageUrl(server.getUserId(), server.getId(), StringUtils.extractFileName(server.getDpUrl())),
                        server.getChannels().stream().map(
                                channel -> modelMapper.map(channel, ChannelDTO.class)).collect(Collectors.toList()
                        )))
                .collect(Collectors.toList());
    }

    public List<ServerDTO> getServersByEmail(String email) {
        // Fetch the userId based on the username
        Account account = accountRepository.findAccountByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Long userId = account.getId();

        // Get all memberships for the user
        List<Membership> memberships = membershipRepository.findByUserId(userId);

        // Fetch and return the servers corresponding to those memberships
        List<Server> servers =  memberships.stream()
                .map(membership -> serverRepository.findById(membership.getServerId())
                        .orElseThrow(() -> new IllegalArgumentException("Server not found")))
                .collect(Collectors.toList());

        // Convert to DTO

        //todo:extract this method to convert server to serverDTO
        return servers.stream()
                .map(server -> new ServerDTO(
                        server.getId(),
                        server.getName(),
                        server.getInviteCode(),
                        server.getUserId(),
                        getServerImageUrl(server.getUserId(), server.getId(), StringUtils.extractFileName(server.getDpUrl())),
                        server.getChannels().stream().map(
                                channel -> modelMapper.map(channel, ChannelDTO.class)).collect(Collectors.toList()
                        )))
                .collect(Collectors.toList());
    }

    @Transactional
    public Channel addChannelToServer(ChannelDTO channel) {
        Server server = serverRepository.findById(channel.getServerId())
                .orElseThrow(() -> new RuntimeException("Server not found"));

        // Check if a channel with the same properties already exists
        boolean channelExists = server.getChannels().stream()
                .anyMatch(existingChannel -> existingChannel.getName().equals(channel.getName()) &&
                        existingChannel.getType().equals(channel.getType()) &&
                        existingChannel.getAdminId().equals(channel.getAdminId()));
        if (channelExists) {
            throw new RuntimeException("A channel with the given name, type, and admin already exists on this server.");
        }

        Channel newChannel = new Channel();
        newChannel.setName(channel.getName());
        newChannel.setType(channel.getType());
        newChannel.setAdminId(channel.getAdminId());

        server.addChannel(newChannel);
        serverRepository.save(server); // Saves both server and the new channel

        // Fetch the newly added channel from the server's channel list
        return server.getChannels().stream()
                .filter(c -> c.getName().equals(newChannel.getName()) &&
                        c.getType().equals(newChannel.getType()) &&
                        c.getAdminId().equals(newChannel.getAdminId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Channel could not be added"));
    }

    public boolean isUserAuthorized(Long userId, Long serverId) {
        // to check whether a user is authorized to access a server, should either be a member or the admin
        return membershipRepository.existsByServerIdAndUserId(serverId, userId);
    }
}
