package hexlet.code;

/**
 * Представляет узел в дереве различий.
 */
public class Node {
    private final String key;
    private final Object oldValue;
    private final Object newValue;
    private final String type;

    /**
     * Конструктор для Node.
     *
     * @param key      ключ
     * @param oldValue старое значение
     * @param newValue новое значение
     * @param type     тип изменения
     */
    public Node(String key, Object oldValue, Object newValue, String type) {
        this.key = key;
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public Object getOldValue() {
        return oldValue;
    }

    public Object getNewValue() {
        return newValue;
    }

    public String getType() {
        return type;
    }
}
