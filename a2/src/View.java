import javax.swing.JPanel;

public abstract interface View
{
  public abstract JPanel accept(Command paramCommand);

  public abstract JPanel checkInPanel();

  public abstract JPanel checkOutPanel();

  public abstract JPanel searchPanel();
}

/* Location:           /home/dmoonleo/Dropbox/HW2/a2.jar
 * Qualified Name:     View
 * JD-Core Version:    0.6.2
 */