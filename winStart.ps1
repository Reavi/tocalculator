$path = "plugin"
If(!(test-path $path))
{
      New-Item -ItemType Directory -Force -Path $path
}
if (Test-Path ".\calculator-main\target\calculator-main-1.0-SNAPSHOT.jar"){
	java -cp calculator-main/target/calculator-main-1.0-SNAPSHOT.jar pl.calculator.App
}
else {
	Write-Warning "Nie wykryto pliku. Instalacja..."
	mvn clean install
	echo "Nacisnij dowolny przycisk aby kontynuowac i wyczyscic konsole"
	$KeyPress = $host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")
	clear
	java -cp calculator-main/target/calculator-main-1.0-SNAPSHOT.jar pl.calculator.App
}