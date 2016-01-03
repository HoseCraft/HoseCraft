package com.mojang.minecraft.net;

public class PacketType {

    // Packet opcodes are assigned in order in which they are defined

    public static final PacketType[] packets = new PacketType[256];
    private static int nextOpcode;

    // ----  STANDARD PACKETS ----------------------------------------------------------------------
    public static final PacketType IDENTIFICATION = // 0 (b)
            new PacketType(Byte.TYPE, String.class, String.class, Byte.TYPE);

    public static final PacketType PING // 1 -- unused (c)
            = new PacketType();

    public static final PacketType LEVEL_INIT // 2 (d)
            = new PacketType();

    public static final PacketType LEVEL_DATA // 3 (e)
            = new PacketType(Short.TYPE, byte[].class, Byte.TYPE);

    public static final PacketType LEVEL_FINALIZE // 4 (f)
            = new PacketType(Short.TYPE, Short.TYPE, Short.TYPE);

    public static final PacketType PLAYER_SET_BLOCK // 5 (g)
            = new PacketType(Short.TYPE, Short.TYPE, Short.TYPE, Byte.TYPE, Byte.TYPE);

    public static final PacketType BLOCK_CHANGE // 6 (h)
            = new PacketType(Short.TYPE, Short.TYPE, Short.TYPE, Byte.TYPE);

    public static final PacketType SPAWN_PLAYER // 7 (i)
            = new PacketType(Byte.TYPE, String.class, Short.TYPE, Short.TYPE,
            Short.TYPE, Byte.TYPE, Byte.TYPE);

    public static final PacketType POSITION_ROTATION // 8 (j)
            = new PacketType(Byte.TYPE, Short.TYPE, Short.TYPE, Short.TYPE, Byte.TYPE, Byte.TYPE);

    public static final PacketType POSITION_ROTATION_UPDATE // 9 (k)
            = new PacketType(Byte.TYPE, Byte.TYPE, Byte.TYPE, Byte.TYPE, Byte.TYPE, Byte.TYPE);

    public static final PacketType POSITION_UPDATE // 10 (l)
            = new PacketType(Byte.TYPE, Byte.TYPE, Byte.TYPE, Byte.TYPE);

    public static final PacketType ROTATION_UPDATE // 11 (m)
            = new PacketType(Byte.TYPE, Byte.TYPE, Byte.TYPE);

    public static final PacketType DESPAWN_PLAYER // 12 (n)
            = new PacketType(Byte.TYPE);

    public static final PacketType CHAT_MESSAGE // 13 (o)
            = new PacketType(Byte.TYPE, String.class);

    public static final PacketType DISCONNECT // 14 (p)
            = new PacketType(String.class);

    public static final PacketType UPDATE_PLAYER_TYPE // 15 (q)
            = new PacketType(Byte.TYPE);

    // ----  CPE PACKETS ---------------------------------------------------------------------------
    public static final PacketType EXT_INFO // 16
            = new PacketType(String.class, Short.TYPE);

    public static final PacketType EXT_ENTRY // 17
            = new PacketType(String.class, Integer.TYPE);

    public static final PacketType CLICK_DISTANCE // 18
            = new PacketType(Short.TYPE);

    public static final PacketType CUSTOM_BLOCK_SUPPORT_LEVEL // 19
            = new PacketType(Byte.TYPE);

    public static final PacketType HOLD_THIS // 20
            = new PacketType(Byte.TYPE, Byte.TYPE);

    public static final PacketType SET_TEXT_HOTKEY // 21
            = new PacketType(String.class, String.class, Integer.TYPE, Byte.TYPE);

    public static final PacketType EXT_ADD_PLAYER_NAME // 22
            = new PacketType(Short.TYPE, String.class, String.class, String.class, Byte.TYPE);

    public static final PacketType EXT_ADD_ENTITY // 23 -- unused
            = new PacketType(Byte.TYPE, String.class, String.class);

    public static final PacketType EXT_REMOVE_PLAYER_NAME // 24
            = new PacketType(Short.TYPE);

    public static final PacketType ENV_SET_COLOR // 25
            = new PacketType(Byte.TYPE, Short.TYPE, Short.TYPE, Short.TYPE);

    public static final PacketType SELECTION_CUBOID // 26
            = new PacketType(Byte.TYPE, String.class, Short.TYPE, Short.TYPE, Short.TYPE,
            Short.TYPE, Short.TYPE, Short.TYPE, Short.TYPE, Short.TYPE, Short.TYPE,
            Short.TYPE);

    public static final PacketType REMOVE_SELECTION_CUBOID // 27
            = new PacketType(Byte.TYPE);

    public static final PacketType SET_BLOCK_PERMISSIONS // 28
            = new PacketType(Byte.TYPE, Byte.TYPE, Byte.TYPE);

    public static final PacketType CHANGE_MODEL // 29
            = new PacketType(Byte.TYPE, String.class);

    public static final PacketType ENV_SET_MAP_APPEARANCE // 30
            = new PacketType(String.class, Byte.TYPE, Byte.TYPE, Short.TYPE);

    public static final PacketType ENV_SET_WEATHER_TYPE // 31
            = new PacketType(Byte.TYPE);

    public static final PacketType HACK_CONTROL // 32
            = new PacketType(Byte.TYPE, Byte.TYPE, Byte.TYPE, Byte.TYPE, Byte.TYPE, Short.TYPE);

    public static final PacketType EXT_ADD_ENTITY2 // 33
            = new PacketType(Byte.TYPE, String.class, String.class, Short.TYPE,
            Short.TYPE, Short.TYPE, Byte.TYPE, Byte.TYPE);

    public int length;
    public byte opcode;
    @SuppressWarnings("rawtypes")
    public Class[] params;

    @SuppressWarnings("rawtypes")
    private PacketType(Class... classes) {
        opcode = (byte) nextOpcode++;
        packets[opcode] = this;
        params = new Class[classes.length];

        int thisPacketLength = 0;

        for (int i = 0; i < classes.length; i++) {
            Class fieldType = classes[i];

            params[i] = fieldType;

            if (fieldType == Long.TYPE) {
                thisPacketLength += 8;
            } else if (fieldType == Integer.TYPE) {
                thisPacketLength += 4;
            } else if (fieldType == Short.TYPE) {
                thisPacketLength += 2;
            } else if (fieldType == Byte.TYPE) {
                thisPacketLength += 1;
            } else if (fieldType == Float.TYPE) {
                thisPacketLength += 4;
            } else if (fieldType == Double.TYPE) {
                thisPacketLength += 8;
            } else if (fieldType == byte[].class) {
                thisPacketLength += 1024;
            } else if (fieldType == String.class) {
                thisPacketLength += 64;
            }
        }

        this.length = thisPacketLength;
    }
}