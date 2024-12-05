package com.sagarsubedi.litcord.dummydata;

import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private List<User> users = Arrays.asList(
            new User(1, "Alice", Arrays.asList(
                    new Server(1, "Server 1", "https://cdn.iconscout.com/icon/premium/png-512-thumb/free-shipping-2523312-2111634.png?f=webp&w=256", Arrays.asList(
                            new Channel(101, "Channel 1", "text"),
                            new Channel(102, "Channel 2", "video")
                    )),
                    new Server(2, "Server 2", "https://cdn.iconscout.com/icon/premium/png-512-thumb/code-36-100919.png?f=webp&w=256", Arrays.asList(
                            new Channel(103, "Channel 3", "text"),
                            new Channel(104, "Channel 4", "video")
                    )),
                    new Server(5, "Server 5", "https://cdn.iconscout.com/icon/premium/png-512-thumb/speaker-404-502682.png?f=webp&w=256", Arrays.asList(
                            new Channel(101, "Channel 1", "text"),
                            new Channel(102, "Channel 2", "video")
                    ))
            )),
            new User(2, "Bob", Arrays.asList(
                    new Server(3, "Server 3", "https://cdn.iconscout.com/icon/premium/png-512-thumb/code-36-100919.png?f=webp&w=256", Arrays.asList(
                            new Channel(105, "Channel 5", "text"),
                            new Channel(106, "Channel 6", "video")
                    ))
            ))
    );

    public Optional<User> getUserById(int userId) {
        return users.stream()
                .filter(user -> user.getId() == userId)
                .findFirst();
    }
}
