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
     * @param keyParam      ключ
     * @param oldValueParam старое значение
     * @param newValueParam новое значение
     * @param typeParam     тип изменения
     */
    public Node(final String keyParam, final Object oldValueParam, final Object newValueParam,
                final String typeParam) {
        this.key = keyParam;
        this.oldValue = oldValueParam;
        this.newValue = newValueParam;
        this.type = typeParam;
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
