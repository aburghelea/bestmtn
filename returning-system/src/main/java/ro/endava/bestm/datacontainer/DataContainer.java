package ro.endava.bestm.datacontainer;

import ro.endava.bestm.entity.ShowInfo;

import java.util.Collection;

/**
 * BEST Engineering Marathon 2014
 * Endava Federated Search, Returning(Callback) System
 *
 * @author <a href="mailto:alexandru.burghelea@endava.com">Alexandru BURGHELEA</a>
 * @since 3/24/14
 */
public interface DataContainer {

    Collection<ShowInfo> get(String name);
}
