package dev.zach473.thegrid.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.UUID;

public record UserIdentifier(UUID uuid, String name) {
    public static final Codec<UserIdentifier> CODEC = RecordCodecBuilder.create(instance -> instance
        .group(
            Codec.STRING.fieldOf("uuid").forGetter((obj) -> obj.uuid.toString()),
            Codec.STRING.fieldOf("name").forGetter(UserIdentifier::name)
        )
        .apply(instance, (uuid, name) -> new UserIdentifier(UUID.fromString(uuid), name))
    );

    public static final StreamCodec<ByteBuf, UserIdentifier> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.STRING_UTF8, (obj) -> obj.uuid.toString(),
        ByteBufCodecs.STRING_UTF8, UserIdentifier::name,
        (uuid, name) -> new UserIdentifier(UUID.fromString(uuid), name)
    );
}
