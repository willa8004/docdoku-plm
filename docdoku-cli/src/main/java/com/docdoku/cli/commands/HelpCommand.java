/*
 * DocDoku, Professional Open Source
 * Copyright 2006 - 2013 DocDoku SARL
 *
 * This file is part of DocDokuPLM.
 *
 * DocDokuPLM is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * DocDokuPLM is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with DocDokuPLM.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.docdoku.cli.commands;


import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineParser;

public class HelpCommand implements CommandLine{

    @Argument(metaVar = "<command>", required = true, index=0, usage = "the command to display the help information")
    private String command;

    public void exec() {
        CommandLine cl;
        switch(command){
            case "status": case "stat": case "st":
                cl=new StatusCommand();
                break;

            case "get":
                cl=new GetCommand();
                break;

            case "put":
                cl=new PutCommand();
                break;

            case "checkout": case "co":
                cl=new CheckOutCommand();
                break;

            case "undocheckout": case "uco":
                cl=new UndoCheckOutCommand();
                break;

            case "checkin": case "ci":
                cl=new CheckInCommand();
                break;

            case "help": case "?" : case "h":
            default:
                cl=new HelpCommand();
        }

        CmdLineParser parser = new CmdLineParser(cl);
        System.out.println(cl.getDescription());
        System.out.println();
        parser.printUsage(System.out);
    }

    @Override
    public String getDescription() {
        return "Describe the usage of this program or its subcommands.";
    }
}
