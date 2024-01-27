import paho.mqtt.client as mqtt
import threading
import time

MQTT_HOST = 'localhost'
MQTT_PORT = 1883
MQTT_COMMAND_TOPIC = 'isat46/command'
MQTT_LOCATION_TOPIC = 'isat46/location'

def start_simulation(mqttClient: mqtt.Client, payload: bytes):
    try:
        command, reservation_id, start_latitude, start_longitude, end_latitude, end_longitude, delay = payload.decode('utf-8').split(';')
        if command == 'BEGIN':
            simulation_thread = threading.Thread(
                target=simulation,
                args=(
                    mqttClient,
                    reservation_id,
                    float(start_latitude),
                    float(start_longitude),
                    float(end_latitude),
                    float(end_longitude),
                    int(delay),
                )
            )
            simulation_thread.start()
            simulation_thread.join()

    except ValueError:
        print("ERROR: could not parse command message")

def simulation(mqttclient: mqtt.Client, reservation_id: str, start_latitude: float, start_longitude: float, end_latitude: float, end_longitude: float, delay: int):
    while start_latitude == end_latitude or start_longitude == end_longitude:
        print(f'{MQTT_LOCATION_TOPIC}/{reservation_id} TEST')
        mqttclient.publish(f'{MQTT_LOCATION_TOPIC}/{reservation_id}', 'test')
        time.sleep(delay)


#MQTT
def mqtt_on_connect(client, userdata, flags, rc):
    print(f'INFO: Connected to: {MQTT_HOST}:{MQTT_PORT}')
    client.subscribe(MQTT_COMMAND_TOPIC)
    client.subscribe(f'{MQTT_LOCATION_TOPIC}/1')

def mqtt_on_message(client, userdata, message):
    print(f'INFO: Message received from: {message.topic}: {message.payload.decode('utf-8')}')

    if(message.topic == MQTT_COMMAND_TOPIC):
        start_simulation(client, message.payload)
    else:
        print("WARNING: Message received from non-command topic, check client subscribtions")

def main():
    client = mqtt.Client()
    client.on_connect = mqtt_on_connect
    client.on_message = mqtt_on_message

    client.connect(MQTT_HOST, MQTT_PORT)
    client.loop_forever() 

if __name__=='__main__':
    main()