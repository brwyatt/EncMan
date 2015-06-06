@echo OFF

echo Jaring Crypto library...
cd Crypto\bin\
jar -cvfm ..\..\Crypto.jar ..\..\Crypto.manifest *
cd ..\..\
echo Done

echo Jaring EncryptionManager...
cd EncryptionManager\bin\
jar -cvfm ..\..\EncryptionManager.jar ..\..\EncryptionManager.manifest *
cd ..\..\
echo Done

pause