# Simple 2b2t queue notifier for Meteor client (Minecraft)

Simple Meteor plugin to send notifications when you have moved in the 2b2t Minecraft server's queue. 2b2t's queue can be 400+ players long at peak times. At the moment, the plugin can only send notifications to a Discord server webhook.

After installing the plugin, you need to set your server's discord webhook in the plugin's settings menu.
<img width="412" alt="image" src="https://github.com/jackpashley/2b2t-queue-notifier/assets/46322193/0255296e-6177-4d07-b838-46c47fc0b13d">

A webhook can be created in Server settings > Integrations > Webhooks > New Webhook
Paste this webhook url into the Discord Webhook field. 

If you've done everything correctly, you'll get a notification in the channel you chose when you set up the webhook when you move 10 positions in the queue. The message is formatted as {player_name} is in position {position} in the 2b2t queue.
