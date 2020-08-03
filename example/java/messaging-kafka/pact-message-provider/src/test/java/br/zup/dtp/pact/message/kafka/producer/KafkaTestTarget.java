package br.zup.dtp.pact.message.kafka.producer;

import au.com.dius.pact.core.model.Interaction;
import au.com.dius.pact.core.model.PactSource;
import au.com.dius.pact.provider.IProviderVerifier;
import au.com.dius.pact.provider.ProviderInfo;
import au.com.dius.pact.provider.junit5.TestTarget;
import java.util.Map;
import kotlin.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class KafkaTestTarget implements TestTarget {

   @NotNull
   @Override
   public Map<String, Object> executeInteraction(@Nullable final Object o, @Nullable final Object o1) {
      return null;
   }

   @NotNull
   @Override
   public ProviderInfo getProviderInfo(@NotNull final String s, @Nullable final PactSource pactSource) {
      return null;
   }

   @Override
   public boolean isHttpTarget() {
      return false;
   }

   @Nullable
   @Override
   public Pair<Object, Object> prepareRequest(@NotNull final Interaction interaction, @NotNull final Map<String, ?> map) {
      return null;
   }

   @Override
   public void prepareVerifier(@NotNull final IProviderVerifier iProviderVerifier, @NotNull final Object o) {

   }
}
