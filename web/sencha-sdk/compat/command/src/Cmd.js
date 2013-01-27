Cmd = {
    execute: function(cmd) {
        if (Platform.isWindows) {
            var stream = new Stream('exec://' + cmd);
            while (!stream.eof) {
                writeln(stream.readln());
            }
            stream.close();
        }
        else {
			var tokens = cmd.split(" ");
		    tokens[0] = tokens[0].replace("java", '/usr/bin/java')
		    cmd = tokens.join(" ");
		    
            system.execute(cmd);
        }
    }
};