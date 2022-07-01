# Install the emulator tool
yes | sdkmanager --install emulator

echo "✨ Installed emulator"

# Install an AOSP image that would later be used as the AVD's OS
yes | sdkmanager --install "system-images;android-29;default;x86_64"

echo "✨ Installed image"

# To do that automatically in a script, try this:
yes | sdkmanager --licenses 

echo "✨ Accepted licences"

# Create an AVD with the image we've previously installed
avdmanager --verbose create avd --force --name Pixel_4_API_29 --abi x86_64 --device "pixel" --package "system-images;android-29;default;x86_64"

echo "✨ Created AVD"

# (optional) Review your brand new AVD:

avdmanager list avd