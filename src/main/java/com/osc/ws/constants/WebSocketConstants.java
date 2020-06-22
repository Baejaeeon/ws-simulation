/*
 * Copyright 2019 The Playce-WASUP Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Revision History
 * Author            Date                Description
 * ---------------  ----------------    ------------
 * Jaeeon Bae       Jul 29, 2019            First Draft.
 */
package com.osc.ws.constants;

/**
 * <pre>
 *
 * </pre>
 *
 * @author Jaeeon Bae
 * @version 1.0
 */
public class WebSocketConstants {

    /**
     * Instantiates a new WebSocket Constants.
     */
    public WebSocketConstants() {

    }

    /**
     * 초기 로그인 정보 전송
     */
    public static final int WS_CODE_LOGIN               = 0x03;

    /**
     * HeartBeat 체크
     */
    public static final int WS_CODE_HEARTBEAT           = 0x04;

    /**
     * 로그인 시 uuid 전송
     */
    public static final int WS_CODE_LOGIN_RESPONSE      = 0x05;

    /**
     * WebSocket 결과 전송
     */
    public static final int WS_CODE_COMMAND_RESPONSE    = 0x10;

    /**
     * The constant WS_QUEUE_USER.
     */
    public static final String WS_QUEUE_USER            = "/user";

    /**
     * The constant WS_QUEUE_REPLY.
     */
    public static final String WS_QUEUE_REPLY           = "/queue/reply";

    /**
     * The constant WS_QUEUE_ERROR.
     */
    public static final String WS_QUEUE_ERROR           = "/queue/error";

    /**
     * The constant WS_QUEUE_ALARM.
     */
    public static final String WS_QUEUE_ALARM           = "/queue/alarm";

    /**
     * The constant WS_APP_HEARTBEAT.
     */
    public static final String WS_APP_HEARTBEAT         = "/app/heartbeat";

    /**
     * The constant WS_APP_INIT.
     */
    public static final String WS_APP_INIT              = "/app/init";
}
//end of WebSocketConstants.java