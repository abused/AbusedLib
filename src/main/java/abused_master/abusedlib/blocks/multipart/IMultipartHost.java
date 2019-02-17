package abused_master.abusedlib.blocks.multipart;

import net.minecraft.util.math.Direction;

import javax.annotation.Nullable;
import java.util.EnumMap;

public interface IMultipartHost {

    boolean tryAddMultipart(Direction direction, IMultipart multipart);

    boolean tryRemoveMultipart(Direction direction, IMultipart multipart);

    boolean hasMultipart(Direction direction);

    @Nullable IMultipart getMultipart(Direction direction);

    EnumMap<Direction, IMultipart> getMultiparts();
}
