/*
 * Copyright (C) 2010-2017 Alibaba Group Holding Limited.
 */

package com.aliyun.demo.crop.media;


import com.aliyun.common.buffer.Allocator;
import com.aliyun.common.buffer.Recycler;

public class BitmapAllocator implements Allocator<ShareableBitmap> {

    private final int width;
    private final int height;

    public BitmapAllocator(int w, int h) {
        width = w;
        height = h;
    }

    @Override
    public ShareableBitmap allocate(Recycler<ShareableBitmap> recycler, ShareableBitmap reused) {
        if (reused != null) {
            reused.reset();
            return reused;
        }

        return new ShareableBitmap(recycler, width, height);
    }

    @Override
    public void recycle(ShareableBitmap object) {

    }

    @Override
    public void release(ShareableBitmap object) {
        object.getData().recycle();
    }

}
