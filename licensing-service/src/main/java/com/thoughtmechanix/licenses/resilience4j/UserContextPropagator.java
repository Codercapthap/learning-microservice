package com.thoughtmechanix.licenses.resilience4j;

import com.thoughtmechanix.licenses.utils.UserContext;
import com.thoughtmechanix.licenses.utils.UserContextHolder;
import io.github.resilience4j.core.ContextPropagator;
import reactor.util.annotation.NonNull;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class UserContextPropagator implements ContextPropagator<UserContext> {

  @Override
  @NonNull
  public Supplier<Optional<UserContext>> retrieve() {
    System.out.println("Getting request tracking id from thread: " + Thread.currentThread().getName());
    return () -> Optional.ofNullable(UserContextHolder.getContext());
  }

  @Override
  @NonNull
  public Consumer<Optional<UserContext>> copy() {
    System.out.println("Getting request tracking id from thread: " + Thread.currentThread().getName());
    return (optionalContext) -> optionalContext.ifPresent(UserContextHolder::setContext);
  }

  @Override
  @NonNull
  public Consumer<Optional<UserContext>> clear() {
    System.out.println("Getting request tracking id from thread: " + Thread.currentThread().getName());
    return context -> UserContextHolder.clear();
  }
}
