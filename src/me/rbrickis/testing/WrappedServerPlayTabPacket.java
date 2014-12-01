package me.rbrickis.testing;

import org.apache.commons.lang.Validate;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Created by Ryan on 11/30/2014
 * <p/>
 * Project: Spigot-1.8
 */
public class WrappedServerPlayTabPacket {

    Class<?> TYPE = ReflectionUtils.getCraftClass("PacketPlayOutPlayerListHeaderFooter");

    Class<?> CHAT_SERIALIZER = ReflectionUtils.getCraftClass("ChatSerializer");

    Class<?> ICHAT_BASE_COMPONENT = ReflectionUtils.getCraftClass("IChatBaseComponent");

    Object packet;

    public WrappedServerPlayTabPacket(String top, String bottom) {
        Validate.notNull(top);
        Validate.notEmpty(top);
        try {
            Constructor<?> packet_construct = TYPE.getConstructor(ICHAT_BASE_COMPONENT);
            // This method is static so invoke 'null' for the object argument.
            Method m = CHAT_SERIALIZER.getMethod("a", String.class);
            Object chat_base_component_object = m.invoke(null, top);
            packet = packet_construct.newInstance(chat_base_component_object);
            ReflectionUtils.setValue(packet, "b", m.invoke(null, bottom));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Object getPacket() {
        return packet;
    }
}
