# Simple 2b2t queue notifier for Meteor client (Minecraft)

Simple Meteor plugin to send notifications when you have moved in the 2b2t Minecraft server's queue. 2b2t's queue can be 400+ players long at peak times. At the moment, the plugin can only send notifications to a Discord server webhook.

After installing the plugin, you need to set your server's discord webhook in the plugin's settings menu.
<img width="412" alt="image" src="https://github.com/jackpashley/2b2t-queue-notifier/assets/46322193/0255296e-6177-4d07-b838-46c47fc0b13d">

A webhook can be created in Server settings > Integrations > Webhooks > New Webhook

You could create your own personal Discord server, or set it up with your friends in your group's server so that you can monitor each other's position in the queue, for whatever reason.

Paste this webhook url into the Discord Webhook field in the plugin's settings menu. 

If you've done everything correctly, you'll get a notification in the channel you chose when you set up the webhook when you move 10 positions in the queue. The message is formatted as {player_name} is in position {position} in the 2b2t queue.

<!-- ROADMAP -->
## Roadmap

- [ ] User can set the magnitude of the movement they want to be alerted for (instead of being alerted every 10 positions, be alerted every 5)
- [ ] Fix issue where alert is skipped if user skips past a position. Currently, if user goes from 361 to 359 instantaneously, no alert is sent.


