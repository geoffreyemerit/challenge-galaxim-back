package galaxim.challenge.util;

import java.util.List;

public interface IRestMethod<T> {
    public List<T> getAll();

    public T getById(Long id);

    public T add(T object);

    public T update(T object, Long id);

    public void delete(Long id);
}
