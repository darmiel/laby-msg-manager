package io.d2a.laby.placeholder;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.d2a.laby.placeholder.annotations.DefaultPattern;
import io.d2a.laby.placeholder.annotations.Placeholder;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;

public class PlaceholderMapper {

  private static String getDefaultPatternFieldValue(final Field field, final Object obj)
      throws IllegalAccessException {

    if (!field.getType().isAssignableFrom(String.class)) {
      return null;
    }
    if (!field.isAnnotationPresent(DefaultPattern.class)) {
      return null;
    }

    // @DefaultPattern fields should be public.
    if (Modifier.isPrivate(field.getModifiers())) {
      return null;
    }

    // should also be static, but we'll ignore that for now.
    if (Modifier.isStatic(field.getModifiers())) {
      return (String) field.get(null);
    } else {
      return (String) field.get(obj);
    }
  }

  public static <T> T map (final Class<T> clazz, String pattern, final String sequence)
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

    // create new object of class T
    final T obj = clazz.getConstructor().newInstance();

    // if no pattern was passed, search in fields
    if (pattern == null) {
      for (final Field field : clazz.getDeclaredFields()) {
        if ((pattern = PlaceholderMapper.getDefaultPatternFieldValue(field, obj)) != null) {
          break;
        }
      }
      // still no luck?
      if (pattern == null) {
        throw new IllegalArgumentException();
      }
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

    // TODO: Add me

    return null;
  }

}
