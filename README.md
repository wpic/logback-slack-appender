This is very simple [Logback](logback) appender to see the logs on your Slack account also.

# How to setup

Copy **com.wpic.logback.SlackAppender.java** to your source directory

Add SlackAppender configuration to logback.xml file

	<?xml version="1.0" encoding="UTF-8" ?>
	<configuration>

		<!-- Setup appender -->
		<appender name="SLACK" class="com.wpic.logback.SlackAppender">
			<!-- API token - Get it from here: https://api.slack.com/ -->
			<token>1111111111-1111111-11111111-111111111</token>
			<!-- Channel that you want to post - default is #general -->
			<channel>#api-test</channel>
			<!-- Formatting -->
			<layout class="ch.qos.logback.classic.PatternLayout">
				<pattern>%-4relative [%thread] %-5level %class - %msg%n</pattern>
			</layout>
		</appender>

		<root>
			<level value="ALL" />
			<appender-ref ref="SLACK" />
		</root>

	</configuration>

Done!