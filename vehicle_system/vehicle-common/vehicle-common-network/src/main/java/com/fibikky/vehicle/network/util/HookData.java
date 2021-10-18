package com.fibikky.vehicle.network.util;

import com.google.protobuf.ByteString;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class HookData<T> {
    T source;
    ByteString data;
}
