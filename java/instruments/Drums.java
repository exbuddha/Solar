package instruments;

public abstract
class Drums
extends MusicalInstrument
{
    public abstract
    class Accessory
    extends MusicalInstrument.Accessory
    {}

    public abstract
    class Pedal
    extends Accessory
    implements instruments.accessory.Pedal
    {}

    public abstract
    class Stand
    extends Accessory
    implements instruments.accessory.Stand
    {}
}