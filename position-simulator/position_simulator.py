import paho.mqtt.client as mqtt
import paho.mqtt.publish as publish
import threading
import time

MQTT_HOST = 'localhost'
MQTT_PORT = 1883
MQTT_COMMAND_TOPIC = 'isat46/command'
MQTT_LOCATION_TOPIC = 'isat46/location'

def start_simulation(payload: bytes):
    try:
        command, reservation_id, start_latitude, start_longitude, end_latitude, end_longitude, delay = payload.decode('utf-8').split(';')
        if command == 'BEGIN':
            simulation_thread = threading.Thread(
                target=simulation,
                args=(
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

def simulation(reservation_id: str, start_latitude: float, start_longitude: float, end_latitude: float, end_longitude: float, delay: int):
    while start_latitude == end_latitude or start_longitude == end_longitude:
        try:

            publish.single(f'{MQTT_LOCATION_TOPIC}/{reservation_id}', 'test', qos=2)
            print(f'{MQTT_LOCATION_TOPIC}/{reservation_id} TEST')
        except:
            print('failed to publish')
        time.sleep(delay)


#MQTT
def mqtt_on_connect(client, userdata, flags, rc):
    print(f'INFO: Connected to: {MQTT_HOST}:{MQTT_PORT}')
    client.subscribe(MQTT_COMMAND_TOPIC)
    client.subscribe(f'{MQTT_LOCATION_TOPIC}/5')

def mqtt_on_message(client, userdata, message):
    if(message.topic == MQTT_COMMAND_TOPIC):
        start_simulation(client, message.payload)
    else:
        print("WARNING: Message received from non-command topic, check client subscribtions")

def main():
    client = mqtt.Client(client_id='POSSIM46')
    client.on_connect = mqtt_on_connect
    client.on_message = mqtt_on_message

    client.connect(MQTT_HOST, MQTT_PORT)
    while True:
        client.loop() 

if __name__=='__main__':
    main()