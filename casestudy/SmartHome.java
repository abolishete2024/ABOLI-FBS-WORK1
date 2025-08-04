package casestudy;

import java.util.*;

// Interfaces
interface MultimediaDevice {
}

interface CoolingDevice {
}

interface WaterDevice {
}

// Abstract Device Class
abstract class Device {
	String deviceId;
	String name;
	boolean isOn;
	long lastOnTime;
	long lastOffTime;
	long totalOnTime;
	long totalOffTime;

	Device(String deviceId, String name) {
		this.deviceId = deviceId;
		this.name = name;
		this.isOn = false;
		this.totalOnTime = 0;
		this.totalOffTime = 0;
		this.lastOffTime = System.currentTimeMillis(); // initially OFF
	}

	void turnOn() {
		if (!isOn) {
			isOn = true;
			long now = System.currentTimeMillis();
			totalOffTime += now - lastOffTime;
			lastOnTime = now;
			System.out.println(name + " (" + deviceId + ") is turned ON.");
		}
	}

	void turnOff() {
		if (isOn) {
			isOn = false;
			long now = System.currentTimeMillis();
			totalOnTime += now - lastOnTime;
			lastOffTime = now;
			System.out.println(name + " (" + deviceId + ") is turned OFF.");
		}
	}

	void getStatus() {
		long now = System.currentTimeMillis();
		long currentOnTime = totalOnTime;
		long currentOffTime = totalOffTime;

		if (isOn) {
			currentOnTime += now - lastOnTime;
		} else {
			currentOffTime += now - lastOffTime;
		}

		System.out.println(name + " (" + deviceId + ") is " + (isOn ? "ON" : "OFF") + ". Total ON time: "
				+ (currentOnTime / 1000) + " sec" + ", OFF time: " + (currentOffTime / 1000) + " sec");
	}
}

// Device Classes
class Light extends Device {
	Light(String deviceId) {
		super(deviceId, "Light");
	}
}

class AC extends Device implements CoolingDevice {
	AC(String deviceId) {
		super(deviceId, "Air Conditioner");
	}
}

class TV extends Device implements MultimediaDevice {
	TV(String deviceId) {
		super(deviceId, "Television");
	}
}

class Shower extends Device implements WaterDevice {
	Shower(String deviceId) {
		super(deviceId, "Shower");
	}
}

class Fan extends Device implements CoolingDevice {
	Fan(String deviceId) {
		super(deviceId, "Fan");
	}
}

class MusicPlayer extends Device implements MultimediaDevice {
	MusicPlayer(String deviceId) {
		super(deviceId, "Music Player");
	}
}

class Geyser extends Device implements WaterDevice {
	Geyser(String deviceId) {
		super(deviceId, "Geyser");
	}
}

class Refrigerator extends Device implements CoolingDevice {
	Refrigerator(String deviceId) {
		super(deviceId, "Refrigerator");
	}
}

class Oven extends Device {
	Oven(String deviceId) {
		super(deviceId, "Oven");
	}
}

class Induction extends Device {
	Induction(String deviceId) {
		super(deviceId, "Induction");
	}
}

class WashingMachine extends Device {
	WashingMachine(String deviceId) {
		super(deviceId, "Washing Machine");
	}
}

// Room class
class Room {
	String name;
	List<Device> devices = new ArrayList<>();
	Scanner scanner = new Scanner(System.in);

	Room(String name) {
		this.name = name;
	}

	void addDevice(Device device) {
		devices.add(device);
		System.out.println(device.name + " (" + device.deviceId + ") added to " + name);
	}

	void listDevices() {
		System.out.println("\nDevices in " + name + ":");
		for (Device d : devices) {
			d.getStatus();
			if (d.isOn) {
				System.out.print("Do you want to turn OFF " + d.name + " (" + d.deviceId + ")? (y/n): ");
				String choice = scanner.nextLine();
				if (choice.equalsIgnoreCase("y")) {
					d.turnOff();
				}
			} else {
				System.out.print("Do you want to turn ON " + d.name + " (" + d.deviceId + ")? (y/n): ");
				String choice = scanner.nextLine();
				if (choice.equalsIgnoreCase("y")) {
					d.turnOn();
				}
			}
		}
	}

	void controlDevice(String deviceId, boolean turnOn) {
		for (Device d : devices) {
			if (d.deviceId.equalsIgnoreCase(deviceId)) {
				if (turnOn)
					d.turnOn();
				else
					d.turnOff();
				return;
			}
		}
		System.out.println("Device with ID " + deviceId + " not found in room " + name);
	}
}

// Main Class
public class SmartHome {
	static Map<String, Room> home = new HashMap<>();
	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		System.out.println("Welcome to Smart Home Automation");

		while (true) {
			System.out.println("\nMenu:");
			System.out.println("1. Add Room");
			System.out.println("2. Add Devices to Room");
			System.out.println("3. Turn ON Device");
			System.out.println("4. Turn OFF Device");
			System.out.println("5. Show Room Status (with ON/OFF Options)");
			System.out.println("6. Exit");
			System.out.print("Enter your choice: ");

			int choice = sc.nextInt();
			sc.nextLine();

			switch (choice) {
			case 1 -> {
				System.out.print("Enter room name: ");
				String roomName = sc.nextLine();
				if (home.containsKey(roomName)) {
					System.out.println("Room already exists.");
				} else {
					home.put(roomName, new Room(roomName));
					System.out.println("Room " + roomName + " added.");
				}
			}

			case 2 -> {
				System.out.print("Enter room name: ");
				String room = sc.nextLine();
				if (!home.containsKey(room)) {
					System.out.println("Room does not exist.");
					break;
				}

				System.out.println("""
						Choose devices to add (use space-separated numbers):
						1. Light
						2. AC
						3. TV
						4. Shower
						5. Fan
						6. Music Player
						7. Geyser
						8. Refrigerator
						9. Oven
						10. Induction
						11. Washing Machine
						""");
				System.out.print("Your choices (e.g., 1 3 5): ");
				String input = sc.nextLine();
				String[] deviceNumbers = input.trim().split("\\s+");

				for (String numStr : deviceNumbers) {
					int deviceChoice;
					try {
						deviceChoice = Integer.parseInt(numStr);
					} catch (NumberFormatException e) {
						System.out.println("Invalid number format: " + numStr);
						continue;
					}

					System.out.print("Enter unique ID for this device: ");
					String id = sc.nextLine();

					Device device = switch (deviceChoice) {
					case 1 -> new Light(id);
					case 2 -> new AC(id);
					case 3 -> new TV(id);
					case 4 -> new Shower(id);
					case 5 -> new Fan(id);
					case 6 -> new MusicPlayer(id);
					case 7 -> new Geyser(id);
					case 8 -> new Refrigerator(id);
					case 9 -> new Oven(id);
					case 10 -> new Induction(id);
					case 11 -> new WashingMachine(id);
					default -> null;
					};

					if (device != null) {
						home.get(room).addDevice(device);
					} else {
						System.out.println("Invalid choice: " + numStr);
					}
				}
			}

			case 3, 4 -> {
				boolean turnOn = (choice == 3);
				System.out.print("Enter room name: ");
				String room = sc.nextLine();
				System.out.print("Enter device ID: ");
				String deviceId = sc.nextLine();
				if (home.containsKey(room)) {
					home.get(room).controlDevice(deviceId, turnOn);
				} else {
					System.out.println("Room not found.");
				}
			}

			case 5 -> {
				System.out.print("Enter room name: ");
				String room = sc.nextLine();
				if (home.containsKey(room)) {
					home.get(room).listDevices();
				} else {
					System.out.println("Room not found.");
				}
			}

			case 6 -> {
				System.out.println("Exiting Smart Home");
				return;
			}

			default -> System.out.println("Invalid option");
			}
		}
	}
}
