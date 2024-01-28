import paho.mqtt.client as mqtt
import paho.mqtt.publish as publish
import threading
import time
import csv

MQTT_HOST = 'localhost'
MQTT_PORT = 1883
MQTT_COMMAND_TOPIC = 'isat46/command'
MQTT_LOCATION_TOPIC = 'isat46/location'
CURRENT_SIMULATIONS = []

def start_simulation(payload: bytes):
    try:
        command, reservation_id, start_latitude, start_longitude, end_latitude, end_longitude, delay = payload.decode('utf-8').split(';')
        if command == 'BEGIN' and reservation_id not in CURRENT_SIMULATIONS:
            CURRENT_SIMULATIONS.append(reservation_id)
            simulation_thread = threading.Thread(
                target=simulation,
                args=(
                    reservation_id,
                    int(delay),
                )
            )
            simulation_thread.start()
            simulation_thread.join()

    except ValueError:
        print("ERROR: could not parse command message")

def simulation(reservation_id: str, delay: int):
    with open('sim-coords.csv') as file:
        reader_obj = csv.DictReader(file)

        for row in reader_obj:
            publish.single(f'{MQTT_LOCATION_TOPIC}/{reservation_id}', f'{row['lat;long']}', qos=2)
            time.sleep(delay)
    CURRENT_SIMULATIONS.remove(reservation_id)

#MQTT
def mqtt_on_connect(client, userdata, flags, rc):
    print(f'INFO: Connected to: {MQTT_HOST}:{MQTT_PORT}')
    client.subscribe(MQTT_COMMAND_TOPIC)

def mqtt_on_message(client, userdata, message):
    if(message.topic == MQTT_COMMAND_TOPIC):
        start_simulation(message.payload)
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