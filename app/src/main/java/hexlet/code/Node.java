package hexlet.code;

/**
 * Представляет узел в дереве различий.
 */
public final class Node {
    /**
     * Ключ узла.
     */
    private final String key;
    /**
     * Старое значение узла.
     */
    private final Object oldValue;
    /**
     * Новое значение узла.
     */
    private final Object newValue;
    /**
     * Тип изменения.
     */
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

    /**
     * Получает ключ узла.
     *
     * @return ключ
     */
    public String getKey() {
        return key;
    }

    /**
     * Получает старое значение узла.
     *
     * @return старое значение
     */
    public Object getOldValue() {
        return oldValue;
    }

    /**
     * Получает новое значение узла.
     *
     * @return новое значение
     */
    public Object getNewValue() {
        return newValue;
    }

    /**
     * Получает тип изменения узла.
     *
     * @return тип изменения
     */
    public String getType() {
        return type;
    }
}
