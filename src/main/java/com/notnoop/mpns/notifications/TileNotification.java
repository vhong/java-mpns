/*
 * Copyright 2011, Mahmood Ali.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 *   * Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above
 *     copyright notice, this list of conditions and the following disclaimer
 *     in the documentation and/or other materials provided with the
 *     distribution.
 *   * Neither the name of Mahmood Ali. nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.notnoop.mpns.notifications;

import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

import com.notnoop.mpns.MpnsNotification;
import static com.notnoop.mpns.internal.Utilities.ifNonNull;
import static com.notnoop.mpns.internal.Utilities.escapeXml;

public class TileNotification implements MpnsNotification {
    private final String backgroundImage;
    private final String title;
    private int count;

    private final String backBackgroundImage;
    private final String backTitle;
    private final String backContent;

    private final List<? extends Entry<String, String>> headers;

    public TileNotification(String backgroundImage, String title, int count,
            String backBackgroundImage, String backTitle, String backContent,
            List<? extends Entry<String, String>> headers) {
        this.backgroundImage = backgroundImage;
        this.title = title;
        this.count = count;

        this.backBackgroundImage = backBackgroundImage;
        this.backTitle = backTitle;
        this.backContent = backContent;

        this.headers = headers;
    }

    public String getRequestBody() {
        String tileMessage =
            "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
            "<wp:Notification xmlns:wp=\"WPNotification\">" +
            "<wp:Tile>" +
              ifNonNull(backgroundImage, "<wp:BackgroundImage>" + escapeXml(backgroundImage) + "</wp:BackgroundImage>") +
              "<wp:Count>" + count + "</wp:Count>" +
              ifNonNull(title, "<wp:Title>" + escapeXml(title) + "</wp:Title>") +
              ifNonNull(backBackgroundImage, "<wp:BackBackgroundImage><back side of tile image path></wp:BackBackgroundImage>") +
              ifNonNull(backTitle, "<wp:BackTitle><back side of tile title></wp:BackTitle>") +
              ifNonNull(backContent, "<wp:BackContent><back side of tile text></wp:BackContent>") +
           "</wp:Tile> " +
        "</wp:Notification>";

        return tileMessage;
    }

    public List<? extends Entry<String, String>> getHttpHeaders() {
        return Collections.unmodifiableList(this.headers);
    }
}
