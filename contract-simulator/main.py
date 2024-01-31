import customtkinter
import json
import paho.mqtt.client as mqtt

customtkinter.set_appearance_mode("light")
customtkinter.set_default_color_theme("green")

app = customtkinter.CTk()
app.geometry("800x800")
app.title("Contract Simulator")
companies = []
hospitals = ["Hospital 1", "Hospital 2", "Hospital 3"]


company_combobox = customtkinter.CTkComboBox(master=app)
company_combobox.set("Select company")
equipment_combobox = customtkinter.CTkComboBox(master=app)
equipment_combobox.set("Select equipment")
day = customtkinter.CTkEntry(app, placeholder_text="Enter day")
count = customtkinter.CTkEntry(app, placeholder_text="Enter equipment count")
hospital_combobox = customtkinter.CTkComboBox(master=app, values=hospitals)
hospital_combobox.set("Select hospital")

def on_connect(client, userdata, flags, rc):
    print("Connected with result code "+str(rc))
    client.subscribe("contract")
    client.subscribe("companies")
    client.subscribe("inventory/low")
    client.subscribe("delivery/success")


def on_message(client, userdata, msg):
    global companies

    if msg.topic == "contract":
        print("contract")
    elif msg.topic == "companies":
        companies = json.loads(msg.payload.decode())
        update_company_combobox()
    elif msg.topic == "inventory/low":
        print("Inventory low message: " + msg.payload.decode())
    elif msg.topic == "delivery/success":
        print("Delivery success message: " + msg.payload.decode())


def update_company_combobox():
    company_names = [company['name'] for company in companies.get('content', [])]
    company_combobox.configure(values=[company for company in company_names], command=pick_company)


def pick_company(choice):
    for company in companies["content"]:
        if company['name'] == choice:
            equipment_combobox.configure(values= [equipment['name'] for equipment in company.get('equipment', [])])
            break

    
def get_companies():
    client.publish("getEquipment", "2")
    client.publish("getCompanies", "getCompanies")

def create_contract():
    hospital = hospital_combobox.get()
    company = company_combobox.get()
    equipment = equipment_combobox.get()
    date = day.get()
    count_value = count.get()

    contract = {
        "hospital": hospital,
        "company_name": company,
        "equipment": equipment,
        "day": date,
        "count": count_value
    }

    contract_json = json.dumps(contract)
    print(client.is_connected())
    try:
        client.publish("contract", contract_json)
    except Exception as e:
        print("Exception while publishing contract:", e)



send = customtkinter.CTkButton(app, text="Send", command=create_contract)

client = mqtt.Client()
client.on_connect = on_connect
client.on_message = on_message
client.connect("localhost", 1883, 60)
client.loop_start()

get_companies()
hospital_combobox.pack(padx=20, pady=10)
company_combobox.configure(width=200)
company_combobox.pack(padx=20, pady=10)
equipment_combobox.configure(width=200)
equipment_combobox.pack(padx=20, pady=10)
label = customtkinter.CTkLabel(app, text="Enter day of the month", fg_color="transparent")
label.pack(padx=20, pady=10)
day.pack(padx=20, pady=10)
label = customtkinter.CTkLabel(app, text="Enter equipment count", fg_color="transparent")
label.pack(padx=20, pady=10)
count.pack(padx=20, pady=10)
send.pack(padx=20, pady=10)

app.mainloop()
client.loop_stop()
