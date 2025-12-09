import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import com.kms.katalon.core.webservice.verification.WSResponseManager as WSResponseManager
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration

// --- Variabel Konfigurasi ---
def kafkaTopic = 'dummy-topic' // <-- GANTI DENGAN NAMA TOPIC ANDA

def timeoutSeconds = 20

// 1. API Producer: Kirim permintaan ke API (Trigger Kafka)
KeywordUtil.logInfo('Memulai Panggilan API untuk memicu pesan Kafka...')

def response = WS.sendRequest(findTestObject('KafkaTest/API_Trigger_Kafka'))

// Verifikasi respons API (Pastikan API berhasil dipanggil)
WS.verifyResponseStatusCode(response, 200)

KeywordUtil.logInfo('Call API Success - Status 200.')


// Verifikasi #1: message not 0
assert messages.size() > 0

// Verifikasi #2: Verifikasi konten pesan
def expectedContent = 'kafka_triggered'

def foundExpectedMessage = false

for (String message : messages) {
    KeywordUtil.logInfo('message received: ' + message)

    if (message.contains(expectedContent)) {
        foundExpectedMessage = true

        break
    }
}

assert foundExpectedMessage

KeywordUtil.markPassed('Test Passed')

