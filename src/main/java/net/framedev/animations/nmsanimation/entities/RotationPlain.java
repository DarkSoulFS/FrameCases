package net.framedev.animations.nmsanimation.entities;

import org.bukkit.Location;

@FunctionalInterface
interface Transformer {
    Location transform(Location astLoc, double offsetX, double offsetY);
}

public enum RotationPlain {

    X((l, x, y) -> l.add(0, y - 1.75, x)),
    Y((l, x, y) -> l.add(x,   - 1.75, y)),
    Z((l, x, y) -> l.add(x, y - 1.75, 0));

    private final Transformer lambda;

    RotationPlain(Transformer lambda) {
        this.lambda = lambda;
    }

    public Location transform(Location astLoc, double offsetX, double offsetY) {
        return lambda.transform(astLoc, offsetX, offsetY);
    }

}
