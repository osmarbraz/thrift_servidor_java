
import org.apache.thrift.TException;

public class CalculadoraHandler implements CalculadoraService.Iface {

    public javax.swing.JTextArea log;

    @Override
    public double multiplicar(double n1, double n2) throws TException {
        //System.out.println("Multiplicar(" + n1 + "," + n2 + ")");
        if (log != null) {
            log.append("Multiplicar(" + n1 + "," + n2 + ")\n");
        }
        return n1 * n2;
    }

    @Override
    public double dividir(double n1, double n2) throws TException {
        //System.out.println("Dividir(" + n1 + "," + n2 + ")");
        if (log != null) {
            log.append("Dividir(" + n1 + "," + n2 + ")\n");
        }
        return n1 / n2;
    }

    @Override
    public double somar(double n1, double n2) throws TException {
        //System.out.println("Somar(" + n1 + "," + n2 + ")");
        if (log != null) {
            log.append("Somar(" + n1 + "," + n2 + ")\n");
        }
        return n1 + n2;
    }

    @Override
    public double subtrair(double n1, double n2) throws TException {
        //System.out.println("Subtrair(" + n1 + "," + n2 + ")");
        if (log != null) {
            log.append("Subtrair(" + n1 + "," + n2 + ")\n");
        }
        return n1 - n2;
    }
}
