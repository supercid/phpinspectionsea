package com.kalessil.phpStorm.phpInspectionsEA.license;

import com.intellij.ide.plugins.IdeaPluginDescriptor;
import com.intellij.notification.*;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

final public class ActivateLicenseAction {
    public void perform(
        @NotNull LicenseService service,
        @NotNull IdeaPluginDescriptor plugin
    ) {
        final String pluginName = plugin.getName();
        final String licenseKey = JOptionPane.showInputDialog(
            null,
            "Please provide a product key",
            String.format("%s activation", pluginName),
            JOptionPane.QUESTION_MESSAGE
        );
        final StringBuilder activationError = new StringBuilder();
        final boolean licenseActivated      = service.applyLicenseKey(licenseKey, activationError);

        final NotificationGroup group = new NotificationGroup(pluginName, NotificationDisplayType.STICKY_BALLOON, true);
        Notifications.Bus.notify(group.createNotification(
            "<b>" + pluginName + "</b>",
            licenseActivated ?
                String.format("Congrats, %s has been successfully activated.", pluginName) :
                String.format("Something went wrong, we were not able to complete the activation: %s", activationError.toString()),
            licenseActivated ?
                NotificationType.INFORMATION :
                NotificationType.WARNING,
            NotificationListener.URL_OPENING_LISTENER
        ));
    }
}