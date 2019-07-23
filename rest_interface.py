import subprocess
import threading
import platform
import time
import sys
import requests
import json
import pprint


MONITORING_URL = 'http://127.0.0.1:8000/api/monitoring/'
BEER_URL = 'http://127.0.0.1:8000/api/beers/'

ENTRY_OPTIONS = "\nOptions:\n'0' to exit\n'1' to show monitored beers\n'2' to update current temperature\nEntry: "

class RestServer(threading.Thread):
    def __init__(self):
        threading.Thread.__init__(self)
        self.daemon = True

    def run(self):
        cmd = None
        if platform.system() == 'Windows':
            cmd = ['start', '/wait', 'mvn', 'clean', 'install', 'exec:java']
        elif platform.system() == 'Darwin':
            cmd = [ 'mvn clean install exec:java' ]
        elif platform.system() == 'Linux':
            cmd = ['open', '-W', '-a', 'Terminial.app', 'mvn', 'clean', 'install', 'exec:java' ]

        self.process = subprocess.Popen(cmd, shell=True)

    def kill(self):
        cmd = None
        if platform.system() == 'Windows':
            cmd = 'taskkill /F /T /PID %i'
        elif platform.system() == 'Darwin':
            cmd = 'kill -9 %i'
        elif platform.system() == 'Linux':
            cmd = 'kill -9 %i'

        subprocess.Popen(cmd % self.process.pid)


def create_default_beers_and_add_to_monitoring():
    default_beers = [
        { 'name': 'Pilsner', 'maxTemp': 6.0, 'minTemp': 4.0 },
        { 'name': 'IPA', 'maxTemp': 6.0, 'minTemp': 5.0 },
        { 'name': 'Lager', 'maxTemp': 7.0, 'minTemp': 4.0 },
        { 'name': 'Stout', 'maxTemp': 8.0, 'minTemp': 5.0 },
        { 'name': 'Wheat beer', 'maxTemp': 5.0, 'minTemp': 3.0 },
        { 'name': 'Pale Ale', 'maxTemp': 6.0, 'minTemp': 4.0 }
    ]

    for beer in default_beers:
        request = requests.post(BEER_URL, data=json.dumps(beer))
        requests.post(MONITORING_URL, data=json.dumps(request.json()))

def change_current_temperature(temp):
    return requests.post(MONITORING_URL + 'temperature/' + temp)

def get_monitored_beers():
    return requests.get(MONITORING_URL).json()

def main():
    try:
        rest_server = RestServer()
        rest_server.start()
        time.sleep(8)
        create_default_beers_and_add_to_monitoring()

        user_input = ''
        while user_input != '0':
            user_input = input(ENTRY_OPTIONS)
            if user_input == '1':
                pprint.pprint(get_monitored_beers())
            if user_input == '2':
                change_current_temperature(input('New temperature: '))
                pprint.pprint(get_monitored_beers())
    except Exception as e:
        print(e)
    finally:
        rest_server.kill()
        sys.exit()

if __name__ == '__main__':
    main()
