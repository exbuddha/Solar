package instruments.generic;

import performance.Group;

public
interface Musical
extends Group
{
    public
    interface BoardedInstrument
    extends Musical
    {}

    public
    interface RhythmicInstrument
    extends Musical
    {}

    public
    interface StringedInstrument
    extends Musical
    {}

    public
    interface WindInstrument
    extends Musical
    {}
}