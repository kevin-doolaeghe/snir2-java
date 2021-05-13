package fr.doolaeghe.tp_afficheur;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

public class AfficheurDM110 {
    private SerialPort serialPort;
    private OutputStream outStream;
    private InputStream inStream;
    
    private String port;
    private int vit;
    private int data;
    private int stop;
    private int parite;
    
    //--------------------------------------------------------------------------------
    
    public AfficheurDM110() throws IOException {
    	port = "COM4";
    	vit = 9600;
    	data = 8;
    	stop = 1;
    	parite = 0;
    	
    	this.connect(port);
    }
    
    public AfficheurDM110(String port) throws IOException {
    	port = this.port;
    	vit = 9600;
    	data = 8;
    	stop = 1;
    	parite = 0;
    	
    	this.connect(port);
    }
    
    public AfficheurDM110(String port, int vit, int data, int parite, int stop) throws IOException {
    	this.port = port;
    	this.vit = vit;
    	this.data = data;
    	this.stop = stop;
    	this.parite = parite;
    	
    	this.connect(port);
    }
    
    public void finalize() {
    	this.disconnect();
    }
 
    private void connect(String portName) throws IOException {
        try {
        	// https://embeddedfreak.wordpress.com/2008/08/08/how-to-open-serial-port-using-rxtx/
            CommPortIdentifier portId = CommPortIdentifier.getPortIdentifier(portName);
 
            serialPort = (SerialPort) portId.open("Demo application", 5000);

            setSerialPortParameters();
 
            outStream = serialPort.getOutputStream();
            inStream = serialPort.getInputStream();
        } catch (NoSuchPortException e) {
        	System.out.println("Port inexistant");
        } catch (PortInUseException e) {
            System.out.println("Port déjà utilisé");
        } catch (IOException e) {
            serialPort.close();
            throw e;
        }
    }
    
    private void disconnect() {
    	serialPort.close();
    }

    //--------------------------------------------------------------------------------
    
    public InputStream getSerialInputStream() { return inStream; }
 
    public OutputStream getSerialOutputStream() { return outStream; }

    /*
    private void setDefaultSerialPortParameters() throws IOException {
        int baudRate = 9600;
        
        try {
            serialPort.setSerialPortParams(baudRate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
            serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
        } catch (UnsupportedCommOperationException ex) {
            throw new IOException("Unsupported serial port parameter");
        }
    }
    */
    
    private void setSerialPortParameters() throws IOException {        
        try {
            serialPort.setSerialPortParams(vit, data, stop, parite);
            serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
        } catch (UnsupportedCommOperationException ex) {
            throw new IOException("Unsupported serial port parameter");
        }
    }
    
    //--------------------------------------------------------------------------------
    
    public void clearScreen() throws IOException {
    	outStream.write(27);
    	outStream.write(64);
    }
    
    public void clearLine() throws IOException {
    	outStream.write(40);
    }
    
    public void moveCursorLeft() throws IOException {
    	outStream.write(8);
    }
    
    public void moveCursorRight() throws IOException {
    	outStream.write(9);
    }
    
    public void resetCursor() throws IOException {
    	outStream.write(31);
    	outStream.write(36);
    	outStream.write(1); //Ligne => de 1 à 20
    	outStream.write(1); //Colonne => 1 ou 2
    }
    
    public void gotoXY(int x, int y) throws IOException {
    	outStream.write(31);
    	outStream.write(36);
    	outStream.write(x);
    	outStream.write(y);
    }
    
    @SuppressWarnings("deprecation")
	public void setTime() throws IOException {
    	Date date = new Date();
    	outStream.write(31);
    	outStream.write(84);
    	outStream.write(date.getHours()); //Heure
    	outStream.write(date.getMinutes()); //Minute
    }
    
    //--------------------------------------------------------------------------------
    
    public void write(String s) throws IOException {
    	outStream.write(s.getBytes());
    }
}