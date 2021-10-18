package com.fibikky.detect.server.inspect.service.inspectContext;

import com.fibikky.detect.server.inspect.models.Frame;

/**
 * @author 16861
 */
public class CombinedFrame {
    public Frame front;
    public Frame behind;

    public CombinedFrame(Frame front, Frame behind) {
        this.front = front;
        this.behind = behind;
    }
}
