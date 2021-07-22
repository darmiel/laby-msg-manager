package io.d2a.laby.placeholder;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.d2a.laby.placeholder.annotations.Placeholder;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PlaceholderMapper {

  public static <T extends Placeholable> T map(
      @Nonnull final Class<T> clazz,
      @Nullable String pattern,
      @Nonnull final String sequence
  )
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

    // create new object of class T
    final T obj = clazz.getConstructor().newInstance();

    // if no pattern was passed, search in fields
    if (pattern == null && (pattern = obj.defaultPattern()) == null) {
      throw new IllegalArgumentException();
    }

    final Map<String, List<Field>> mappedFields = Maps.newHashMap();
    for (final Field field : clazz.getDeclaredFields()) {
      if (!field.isAnnotationPresent(Placeholder.class)) {
        continue;
      }
      final Placeholder placeholder = field.getAnnotation(Placeholder.class);
      if (!mappedFields.containsKey(placeholder.value())) {
        mappedFields.put(placeholder.value(), Lists.newArrayList());
      }
      // add field to placeholder's list
      mappedFields.get(placeholder.value())
          .add(field);
    }

    System.out.println("Fields: " + mappedFields);
    // TODO: Add me

    return obj;
  }

  public static <T extends Placeholable> Optional<T> mapUnsafe(
      @Nonnull final Class<T> clazz,
      @Nullable String pattern,
      @Nonnull final String sequence
  ) {
    try {
      return Optional.of(map(clazz, pattern, sequence));
    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
      e.printStackTrace();
    }
    return Optional.empty();
  }

}
