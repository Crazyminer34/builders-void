package dev.detpikachu.buildersvoid.state.containers;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

public record ReturnPosition(ResourceLocation dimension, Vec3 position) {

    public CompoundTag serialize() {

        CompoundTag tag = new CompoundTag();
        tag.putString("Dimension", dimension.toString());

        CompoundTag positionTag = new CompoundTag();
        positionTag.putDouble("X", position.x);
        positionTag.putDouble("Y", position.y);
        positionTag.putDouble("Z", position.z);
        tag.put("Position", positionTag);

        return tag;
    }

    public static ReturnPosition deserialize(CompoundTag tag) {

        String dimensionString = tag.getString("Dimension");
        ResourceLocation dimension = ResourceLocation.tryParse(dimensionString);

        CompoundTag positionTag = tag.getCompound("Position");
        Vec3 position = new Vec3(
            positionTag.getFloat("X"),
            positionTag.getFloat("Y"),
            positionTag.getFloat("Z")
        );

        return new ReturnPosition(dimension, position);
    }
}
