package com.p3t0plugin.villagenetwork.models;

import org.bukkit.Location;

import java.util.UUID;

public class Village {

    private final UUID ownerId;
    private final String name;
    private Location home;

    public Village(UUID ownerId, String name, Location home) {
        this.ownerId = ownerId;
        this.name = name;
        this.home = home;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public String getName() {
        return name;
    }

    public Location getHome() {
        return home;
    }

    public void setHome(Location home) {
        this.home = home;
    }
}
