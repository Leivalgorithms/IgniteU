package com.example.igniteu.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;



import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import com.example.igniteu.models.Usertable;
import java.util.Random;

@Controller
public class PasswordRecoveryController {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Página para solicitar el correo electrónico
    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "forgot-password";
    }

    // Procesar el correo electrónico y enviar OTP
    @PostMapping("/send-otp")
    public String sendOTP(@RequestParam("email") String email, Model model) {
        // Verificar si el correo existe en la base de datos
        System.out.println(email);
        String sql = "SELECT * FROM usuarios WHERE correo = ?";
        @SuppressWarnings("deprecation")
        Usertable usuario = jdbcTemplate.queryForObject(sql, new Object[]{email}, (rs, rowNum) -> {
            Usertable u = new Usertable();
            u.setUsername(rs.getString("username"));
            u.setCorreo(rs.getString("correo"));
            u.setContrasena(rs.getString("contrasena"));
            return u;
        });

        if (usuario == null) {
            model.addAttribute("error", "Correo electrónico no encontrado");
            return "forgot-password";
        }

        // Generar OTP aleatorio
        int otp = new Random().nextInt(900000) + 100000;
        
        
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Recuperación de contraseña - OTP");
        message.setText("Su código OTP es: " + otp);
        emailSender.send(message);

        String insertOtpSql = "INSERT INTO otp_table (correo, otp) VALUES (?, ?)";
        jdbcTemplate.update(insertOtpSql, email, otp);
        // Guardar el OTP en la sesión o en la base de datos temporalmente

        model.addAttribute("email", email);
        return "verify-otp";
    }

    // Página para verificar OTP
    @GetMapping("/verify-otp")
    public String showVerifyOTPForm() {
        return "verify-otp";
    }

    // Procesar OTP ingresado por el usuario
    @PostMapping("/verify-otp")
    public String verifyOTP(@RequestParam("otp") int otp, @RequestParam("email") String email, Model model) {
        String sql = "SELECT otp FROM otp_table WHERE correo = ?";
        @SuppressWarnings("deprecation")
        Integer storedOtp = jdbcTemplate.queryForObject(sql, new Object[]{email}, Integer.class);

        if (storedOtp == null || storedOtp != otp) {
            model.addAttribute("error", "OTP inválido");
            return "verify-otp";
        }

        // OTP válido, proceder al cambio de contraseña
        model.addAttribute("email", email);
        return "change-password";
    }

    // Página para cambiar la contraseña
    @GetMapping("/change-password")
    public String showChangePasswordForm() {
        return "change-password";
    }

    // Procesar el cambio de contraseña
    @PostMapping("/change-password")
    public String changePassword(@RequestParam("password") String newPassword,@RequestParam("password-confirm") String passwordConfirm, @RequestParam("email") String email, Model model) {
        if(newPassword.equals(passwordConfirm)) {
        String sql = "UPDATE usuarios SET contrasena = ? WHERE correo = ?";
        jdbcTemplate.update(sql, newPassword, email);
         // Eliminar el OTP de la base de datos
         String deleteOtpSql = "DELETE FROM otp_table WHERE correo = ?";
         jdbcTemplate.update(deleteOtpSql, email);
 
         model.addAttribute("message", "Contraseña cambiada exitosamente");
         return "login";

        }
        else {
            model.addAttribute("error", "Las contraseñas no coinciden");
            return "change-password";
        }
        
    }
}