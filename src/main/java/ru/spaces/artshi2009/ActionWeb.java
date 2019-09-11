package ru.spaces.artshi2009;

import com.google.common.base.Throwables;
import io.github.bonigarcia.wdm.DriverManagerType;
import io.github.bonigarcia.wdm.FirefoxDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.ArrayList;
import java.util.List;


class ActionWeb {
    private WebDriver driver;
    private LogFile log;
    private int reconnectTry;

    ActionWeb(){
        log = new LogFile();
        reconnectTry = 1;
    }

    void RunActionWeb(){
        FirefoxDriverManager.getInstance(DriverManagerType.FIREFOX).setup();
        driver = new FirefoxDriver(new FirefoxOptions().setHeadless(true));

        for (int i = 1; i <= reconnectTry; i++) {
            try {
                driver.get(Params.getLoginPage());
                loginZabbix(driver, 3000, Params.getLogin(), Params.getPassword());
                driver.get(Params.getDashboardPage());
                searchAlert(driver, 2000);
                closingAlert(driver, 2000);
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
            } finally {
                driver.quit();
            }
        }
    }

    private void closingAlert(WebDriver driver, int waitRun) throws InterruptedException {
        Thread.sleep(waitRun);
        if (driver.findElement(By.className("table-forms-td-right")).findElement(By.tagName("textarea")).getText().isEmpty()){
            driver.findElement(By.tagName("textarea")).sendKeys("Закрыто инженером мониторинга");
            driver.findElement(By.id("close_problem")).click();
            driver.findElement(By.id("acknowledge_problem")).click();
            driver.findElement(By.name("action")).click();
        }

        log.logFile("Инцидент закрыт.");
    }

    private void searchAlert(WebDriver driver, int waitRun) throws InterruptedException {
        Thread.sleep(waitRun);
        if (driver.findElement(By.className("list-table")).findElement(By.linkText("mail-relay1")).isEnabled()){
            List<WebElement> elements = driver.findElements(By.tagName("tr"));
            for (int i = 0; i < elements.size(); i++){
                if (elements.get(i).getText().indexOf("mail-relay1") != -1) {
                    elements.get(i).findElement(By.linkText("Нет")).click();
                    break;
                }
            }
        }

        log.logFile("Событие найдено.");
    }

    private void loginZabbix(WebDriver driver, int waitRun, String login, String password) throws InterruptedException {
        Thread.sleep(waitRun);
        if (driver.findElement(By.name("name")).isEnabled()) {
            driver.findElement(By.id("name")).sendKeys(login);
            driver.findElement(By.id("password")).sendKeys(password);
            driver.findElement(By.id("enter")).click();
        }
		
        log.logFile("Авторизация прошла успешно.");
    }
}
