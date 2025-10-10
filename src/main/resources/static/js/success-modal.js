// Script para modal de registro exitoso con redirección automática
function showSuccessModal(message) {
    // Crear overlay oscuro
    const overlay = document.createElement('div');
    overlay.style.cssText = `
        position: fixed;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background: rgba(0, 0, 0, 0.8);
        z-index: 10000;
        display: flex;
        align-items: center;
        justify-content: center;
        animation: fadeIn 0.3s ease;
    `;
    
    // Crear modal de éxito
    const modal = document.createElement('div');
    modal.style.cssText = `
        background: white;
        padding: 50px 40px;
        border-radius: 20px;
        text-align: center;
        max-width: 450px;
        width: 90%;
        box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);
        animation: slideDown 0.5s ease;
        position: relative;
    `;
    
    modal.innerHTML = `
        <div style="font-size: 80px; color: #51cf66; margin-bottom: 20px; animation: checkmark 0.6s ease;">
            <i class="fa-solid fa-circle-check"></i>
        </div>
        <h2 style="color: #333; margin-bottom: 15px; font-size: 28px; font-weight: bold;">
            ¡Registro Exitoso!
        </h2>
        <p style="color: #666; font-size: 16px; line-height: 1.6; margin-bottom: 25px;">
            ${message}
        </p>
        <p style="color: #999; font-size: 14px; margin-bottom: 20px;">
            Redirigiendo al login en <span id="countdown" style="color: #007bff; font-weight: bold;">3</span> segundos...
        </p>
        <div style="width: 60px; height: 60px; border: 4px solid #f3f3f3; border-top: 4px solid #007bff; border-radius: 50%; margin: 0 auto; animation: spin 1s linear infinite;"></div>
    `;
    
    overlay.appendChild(modal);
    document.body.appendChild(overlay);
    
    // Contador regresivo
    let seconds = 3;
    const countdownElement = document.getElementById('countdown');
    
    const countdownInterval = setInterval(() => {
        seconds--;
        if (countdownElement) {
            countdownElement.textContent = seconds;
        }
        if (seconds <= 0) {
            clearInterval(countdownInterval);
        }
    }, 1000);
    
    // Redirigir después de 3 segundos
    setTimeout(() => {
        window.location.href = '/login';
    }, 3000);
    
    // Agregar estilos de animación
    const style = document.createElement('style');
    style.textContent = `
        @keyframes fadeIn {
            from { opacity: 0; }
            to { opacity: 1; }
        }
        @keyframes slideDown {
            from { 
                opacity: 0; 
                transform: translateY(-100px) scale(0.8);
            }
            to { 
                opacity: 1; 
                transform: translateY(0) scale(1);
            }
        }
        @keyframes spin {
            to { transform: rotate(360deg); }
        }
        @keyframes checkmark {
            0% { 
                transform: scale(0) rotate(0deg);
                opacity: 0;
            }
            50% {
                transform: scale(1.2) rotate(180deg);
            }
            100% { 
                transform: scale(1) rotate(360deg);
                opacity: 1;
            }
        }
    `;
    document.head.appendChild(style);
}