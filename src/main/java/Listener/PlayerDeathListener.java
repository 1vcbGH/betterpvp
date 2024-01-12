package Listener;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerDeathListener implements Listener {

    private final JavaPlugin plugin;

    public PlayerDeathListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        // Verificar si el causante de la muerte fue otro jugador
        if (event.getEntity().getKiller() instanceof Player) {
            Player killer = event.getEntity().getKiller();

            // Lógica para otorgar dinero usando Vault
            if (setupEconomy()) {
                Economy economy = plugin.getServer().getServicesManager().getRegistration(Economy.class).getProvider();
                double amount = 10.0; // Puedes ajustar la cantidad de dinero que se otorga

                economy.depositPlayer(killer, amount);
                killer.sendMessage("Has recibido $" + amount + " por matar a un jugador.");
            }
        }
    }

    private boolean setupEconomy() {
        if (plugin.getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }

        RegisteredServiceProvider<Economy> rsp = plugin.getServer().getServicesManager().getRegistration(Economy.class);
        return rsp != null;
    }
}

