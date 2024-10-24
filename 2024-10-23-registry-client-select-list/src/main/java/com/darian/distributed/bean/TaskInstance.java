package com.darian.distributed.bean;

import lombok.Data;

import java.util.Objects;

/***
 *
 *
 * @author <a href="mailto:">notOnlyGeek</a>
 * @date 2024/10/24  06:59
 */
@Data
public class TaskInstance {
    private String Host;
    private int Port;

    public TaskInstance(String host, int port) {
        Host = host;
        Port = port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskInstance that = (TaskInstance) o;
        return Port == that.Port && Objects.equals(Host, that.Host);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Host, Port);
    }
}
