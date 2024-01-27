import paho.mqtt.client as mqtt

MQTT_HOST = 'localhost'
MQTT_PORT = 1883
MQTT_COMMAND_TOPIC = 'isat46/command'
MQTT_LOCATION_TOPIC = 'isat46/location'

def mqtt_on_connect(client, userdata, flags, rc):
    print(f'INFO: Connected to: {MQTT_HOST}:{MQTT_PORT}')
    client.subscribe(MQTT_COMMAND_TOPIC)
    client.subscribe(f'{MQTT_LOCATION_TOPIC}/1')