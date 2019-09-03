package ru.spaces.artshi2009;

import com.google.common.base.Throwables;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import static io.github.bonigarcia.wdm.DriverManagerType.CHROME;


class ActionWeb {
    private LogFile log = new LogFile();
    private int reconnectTry = 1;

    void RunActionWeb(){
        ChromeDriverManager.getInstance(CHROME).setup();
        WebDriver driver = new ChromeDriver();

        for (int i = 1; i <= reconnectTry; i++) {
            try {
                driver.get(Params.getLoginPage());
                loginZabbix(driver, 5000, Params.getLogin(), Params.getPassword());
                driver.get(Params.getDashboardPage());
                searchAlert(driver, 3000);
                closingAlert(driver, 3000);
            } catch (NoSuchElementException | InterruptedException ex) {
                log.logFile("Не обнаружен элемент при поиске на странице: \n" +
                        Throwables.getStackTraceAsString(ex));
                ex.printStackTrace();
            } catch (SessionNotCreatedException ex) {
                log.logFile("Не удалось установить соедиение: \n" +
                        Throwables.getStackTraceAsString(ex));
                ex.printStackTrace();
                reconnectTry = 3;
            } catch (WebDriverException ex) {
                log.logFile("Непредвиденная ошибка: \n" +
                        Throwables.getStackTraceAsString(ex));
                ex.printStackTrace();
            }
        }

        driver.quit();
    }

    private void closingAlert(WebDriver driver, int waitRun) throws InterruptedException {
        Thread.sleep(waitRun);
        if (driver.findElement(By.className("table-forms-td-right")).findElement(By.tagName("textarea")).getText().isEmpty()){
            driver.findElement(By.tagName("textarea")).sendKeys("Закрыто инженером мониторинга");
            driver.findElement(By.id("close_problem")).click();
            driver.findElement(By.id("acknowledge_problem")).click();
            driver.findElement(By.name("action")).click();
        }
    }

    private void searchAlert(WebDriver driver, int waitRun) throws InterruptedException {
        Thread.sleep(waitRun);
        if (driver.findElement(By.className("list-table")).findElement(By.linkText("mail-relay1 (10.2.12.132)")).isEnabled()){
            driver.findElement(By.linkText("Нет")).click();
        }
    }

    private void loginZabbix(WebDriver driver, int waitRun, String login, String password) throws InterruptedException {
        Thread.sleep(waitRun);
        if (driver.findElement(By.name("name")).isEnabled()){
            driver.findElement(By.id("name")).sendKeys(login);
            driver.findElement(By.id("password")).sendKeys(password);
            driver.findElement(By.id("enter")).click();
        }
    }
}
