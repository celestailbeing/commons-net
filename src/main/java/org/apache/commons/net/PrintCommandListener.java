/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.commons.net;

import java.io.PrintWriter;
import org.apache.commons.net.ProtocolCommandEvent;
import org.apache.commons.net.ProtocolCommandListener;

/***
 * This is a support class for some of the example programs.  It is
 * a sample implementation of the ProtocolCommandListener interface
 * which just prints out to a specified stream all command/reply traffic.
 * <p>
 *
 * @since 2.0
 ***/

public class PrintCommandListener implements ProtocolCommandListener
{
    private final PrintWriter __writer;
    private final boolean __nologin;

    /**
     * Create the default instance which prints everything.
     * 
     * @param writer where to write the commands and responses
     */
    public PrintCommandListener(PrintWriter writer)
    {
        this(writer, false);
    }

    /**
     * Create an instance which optionally suppresses login command text.
     * 
     * @param writer where to write the commands and responses
     * @param suppressLogin if {@code true}, only print command name for login
     * 
     * @since 3.0
     */
    public PrintCommandListener(PrintWriter writer, boolean suppressLogin)
    {
        __writer = writer;
        __nologin = suppressLogin;
    }

    public void protocolCommandSent(ProtocolCommandEvent event)
    {
        if (__nologin) {
            String cmd = event.getCommand();
            if ("PASS".equalsIgnoreCase(cmd) || "USER".equalsIgnoreCase(cmd)) {
                __writer.print(cmd);
                __writer.println(" *******");
            } else {
                __writer.print(event.getMessage());
            }
        } else {
            __writer.print(event.getMessage());
        }
        __writer.flush();
    }

    public void protocolReplyReceived(ProtocolCommandEvent event)
    {
        __writer.print(event.getMessage());
        __writer.flush();
    }
}

