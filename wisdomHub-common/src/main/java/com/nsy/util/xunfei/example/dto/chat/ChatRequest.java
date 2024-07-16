package com.nsy.util.xunfei.example.dto.chat;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;

/**
 * ChatRequest
 *
 * @author ydwang16
 * @version 2023/06/21 15:40
 **/
@Getter
@Setter
@Builder
public class ChatRequest {

    private List<String> fileIds;

    private List<ChatMessage> messages;

    private Integer topN;
}