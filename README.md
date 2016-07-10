=How to use=

==install ant==
sudo apt install ant

==build, install, test==
ant prepare

==make sure your sdk version==
project.properties
==make sure your test
like this:
com.cloudminds.teminal.smoke.SmokeTest#testBrowser

    <target name="test" description="Runs tests">
         <exec executable="${adb}" failonerror="true">
            <arg line="${adb.device.arg}" />
            <arg value="shell" />
            <arg value="uiautomator" />
            <arg value="runtest" />
            <arg value="${out.filename}" />
            <arg value="-e" />
            <arg value="class" />
            <arg value="com.cloudminds.teminal.smoke.SmokeTest#testBrowser" />
        </exec>
    </target>
  
