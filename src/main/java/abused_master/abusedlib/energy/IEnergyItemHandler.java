package abused_master.abusedlib.energy;

public interface IEnergyItemHandler extends IEnergyHandler {

    boolean receiveEnergy(int amount);
    boolean extractEnergy(int amount);
}
